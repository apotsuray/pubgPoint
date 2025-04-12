// Получаем элементы
const tabButtons = document.querySelectorAll('.tab-button');
const mapContainer = document.getElementById('map');
const addPointToggle = document.getElementById('add-point-toggle');
const editPointToggle = document.getElementById('edit-point-toggle');
const editPanel = document.getElementById('edit-panel');
const editForm = document.getElementById('edit-point-form');
const deleteButton = document.getElementById('delete-point');
const closeEditPanelButton = document.getElementById('close-edit-panel');

let currentIslandId = 1; // ID текущего острова
let isAddingPoints = false; // Флаг для режима добавления точек
let isEditingPoints = false; // Флаг для режима редактирования точек
let map; // Объект карты Leaflet
let markers = []; // Массив для хранения маркеров
let selectedMarker = null; // Выбранный маркер

// Настройки карты
const mapSettings = {
    erangel: {
        center: [512, 512],
        zoom: 2,
        imageUrl: '/images/erangel.png',
        imageBounds: [[0, 0], [1024, 1024]]
    },
    miramar: {
        center: [512, 512],
        zoom: 2,
        imageUrl: '/images/miramar.png',
        imageBounds: [[0, 0], [1024, 1024]]
    },
    vikendi: {
        center: [512, 512],
        zoom: 2,
        imageUrl: '/images/vikendi.png',
        imageBounds: [[0, 0], [1024, 1024]]
    },
    rondo: {
        center: [512, 512],
        zoom: 2,
        imageUrl: '/images/rondo.png',
        imageBounds: [[0, 0], [1024, 1024]]
    },
    taego: {
        center: [512, 512],
        zoom: 2,
        imageUrl: '/images/taego.png',
        imageBounds: [[0, 0], [1024, 1024]]
    },
    deston: {
        center: [512, 512],
        zoom: 2,
        imageUrl: '/images/deston.png',
        imageBounds: [[0, 0], [1024, 1024]]
    }
};

// Создание кастомной иконки для маркеров
const redDotIcon = L.divIcon({
    className: 'red-dot-icon',
    iconSize: [6, 6],
    iconAnchor: [3, 3]
});

// Функция для очистки маркеров
function clearMarkers() {
    markers.forEach(marker => marker.remove());
    markers = [];
}

// Функция для загрузки точек
async function loadPoints(islandId) {
    try {
        const response = await fetch(`/api/point/island/${islandId}`);
        if (!response.ok) throw new Error('Ошибка загрузки точек');

        const points = await response.json();
        clearMarkers();

        points.forEach(point => {
            const marker = L.marker([point.yCoord, point.xCoord], { icon: redDotIcon }).addTo(map);
            marker.bindPopup(`Рейтинг: ${point.baseRating}<br>${point.description}`);
            marker.pointData = point; // Сохраняем данные точки в маркере
            markers.push(marker);

            // Если режим редактирования включён, добавляем обработчик клика
            if (isEditingPoints) {
                marker.on('click', () => openEditPanel(marker));
            }
        });
    } catch (error) {
        console.error(error);
    }
}

// Функция для открытия панели редактирования
function openEditPanel(marker) {
    selectedMarker = marker;
    const point = marker.pointData;

    // Заполняем форму данными точки
    document.getElementById('edit-xCoord').value = point.xCoord;
    document.getElementById('edit-yCoord').value = point.yCoord;
    document.getElementById('edit-baseRating').value = point.baseRating;
    document.getElementById('edit-description').value = point.description;

    // Показываем панель
    editPanel.classList.remove('hidden');
}

// Функция для закрытия панели редактирования
function closeEditPanel() {
    editPanel.classList.add('hidden');
    selectedMarker = null;
}

// Обработчик отправки формы редактирования
editForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    if (!selectedMarker) return;

    const updatedPoint = {
        id: selectedMarker.pointData.id,
        xCoord: parseFloat(document.getElementById('edit-xCoord').value),
        yCoord: parseFloat(document.getElementById('edit-yCoord').value),
        baseRating: parseInt(document.getElementById('edit-baseRating').value),
        description: document.getElementById('edit-description').value
    };

    try {
        const response = await fetch(`/api/point/${updatedPoint.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedPoint)
        });

        if (!response.ok) throw new Error('Ошибка обновления точки');

        // Обновляем данные маркера
        selectedMarker.pointData = updatedPoint;
        selectedMarker.setLatLng([updatedPoint.yCoord, updatedPoint.xCoord]);
        selectedMarker.unbindPopup();
        selectedMarker.bindPopup(`Рейтинг: ${updatedPoint.baseRating}<br>${updatedPoint.description}`);

        closeEditPanel();
    } catch (error) {
        console.error(error);
    }
});

// Обработчик удаления точки
deleteButton.addEventListener('click', async () => {
    if (!selectedMarker) return;

    const pointId = selectedMarker.pointData.id;

    try {
        const response = await fetch(`/api/point/${pointId}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Ошибка удаления точки');

        // Удаляем маркер с карты
        selectedMarker.remove();
        markers = markers.filter(marker => marker.pointData.id !== pointId);

        closeEditPanel();
    } catch (error) {
        console.error(error);
    }
});

// Обработчик закрытия панели
closeEditPanelButton.addEventListener('click', () => {
    closeEditPanel();
});

// Инициализация карты
function initMap(islandName) {
    const settings = mapSettings[islandName];

    if (map) map.remove();

    map = L.map('map', {
        crs: L.CRS.Simple,
        attributionControl: false
    });

    const bounds = L.latLngBounds(settings.imageBounds);
    map.fitBounds(bounds);

    L.imageOverlay(settings.imageUrl, settings.imageBounds).addTo(map);
    loadPoints(currentIslandId);
}

// Обработчик кликов на вкладки
tabButtons.forEach(button => {
    button.addEventListener('click', () => {
        tabButtons.forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');

        const islandName = button.getAttribute('data-island');
        currentIslandId = button.getAttribute('data-id');
        initMap(islandName);
    });
});

// Обработчик клика на карте для добавления точки
mapContainer.addEventListener('click', (event) => {
    if (!map) return;
    const latlng = map.mouseEventToLatLng(event);
    addPoint({ latlng });
});

// Функция для добавления точки на карту
function addPoint(event) {
    if (!isAddingPoints) return;

    const { latlng } = event;
    const xCoord = latlng.lng;
    const yCoord = latlng.lat;

    console.log(`Добавлена точка: x=${xCoord}, y=${yCoord}`);

    // Отправляем точку на сервер
    fetch('/api/point', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            island: { id: currentIslandId },
            xCoord: xCoord,
            yCoord: yCoord,
            baseRating: 5, // Пример значения рейтинга
        })
    })
        .then(response => {
            if (!response.ok) throw new Error('Ошибка добавления точки');
            return response.json();
        })
        .then(newPoint => {
            // Создаём маркер для новой точки
            const marker = L.marker([newPoint.yCoord, newPoint.xCoord], { icon: redDotIcon }).addTo(map);
            marker.bindPopup(`Рейтинг: ${newPoint.baseRating}<br>${newPoint.description}`);
            marker.pointData = newPoint;
            markers.push(marker);

            // Добавляем обработчик клика на маркер
            if (isEditingPoints) {
                marker.on('click', () => openEditPanel(marker));
            }
        })
        .catch(error => {
            console.error(error);
        });
}

// Обработчик переключения режима добавления точек
addPointToggle.addEventListener('click', () => {
    isAddingPoints = !isAddingPoints;
    addPointToggle.textContent = isAddingPoints ? 'Отключить добавление точек' : 'Включить добавление точек';
    addPointToggle.classList.toggle('active');
});

// Обработчик переключения режима редактирования точек
editPointToggle.addEventListener('click', () => {
    isEditingPoints = !isEditingPoints;
    editPointToggle.textContent = isEditingPoints ? 'Отключить редактирование точек' : 'Включить редактирование точек';
    editPointToggle.classList.toggle('active');

    if (isEditingPoints) {
        // Включаем обработчики клика на маркеры
        markers.forEach(marker => {
            marker.on('click', () => openEditPanel(marker));
        });
    } else {
        // Отключаем обработчики клика на маркеры
        markers.forEach(marker => {
            marker.off('click');
        });
        closeEditPanel(); // Закрываем панель редактирования, если она открыта
    }
});

// Инициализация карты при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    initMap('erangel');
});