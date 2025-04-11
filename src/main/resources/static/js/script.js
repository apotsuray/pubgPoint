// Получаем элементы
const tabButtons = document.querySelectorAll('.tab-button');
const mapContainer = document.getElementById('map');
const addPointToggle = document.getElementById('add-point-toggle');

let currentIslandId = 1; // ID текущего острова
let isAddingPoints = false; // Флаг для режима добавления точек
let map; // Объект карты Leaflet
let markers = []; // Массив для хранения маркеров

// Настройки карты
const mapSettings = {
    erangel: {
        center: [512, 512], // Центр карты в пикселях
        zoom: 2,
        imageUrl: '/images/erangel.png',
        imageBounds: [[0, 0], [1024, 1024]] // Границы изображения в пикселях
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
    className: 'red-dot-icon', // Класс для стилизации
    iconSize: [6, 6], // Размер иконки (6x6 пикселей)
    iconAnchor: [3, 3] // Центр иконки (половина размера)
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
            markers.push(marker);
        });
    } catch (error) {
        console.error(error);
    }
}

// Функция для добавления точки на карту
function addPoint(event) {
    if (!isAddingPoints) return;

    const { latlng } = event;
    const xCoord = latlng.lng; // Пиксельная координата X
    const yCoord = latlng.lat; // Пиксельная координата Y

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
            description: 'Новая точка' // Пример описания
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
            markers.push(marker);
        })
        .catch(error => {
            console.error(error);
        });
}

// Инициализация карты
function initMap(islandName) {
    const settings = mapSettings[islandName];

    // Удаляем предыдущую карту
    if (map) map.remove();

    // Создаём новую карту с пиксельными координатами
    map = L.map('map', {
        crs: L.CRS.Simple, // Используем пиксельную систему координат
        attributionControl: false
    });

    // Задаём границы изображения
    const bounds = L.latLngBounds(settings.imageBounds);
    map.fitBounds(bounds); // Подгоняем карту под границы изображения

    // Добавляем изображение как оверлей
    L.imageOverlay(settings.imageUrl, settings.imageBounds).addTo(map);

    // Загружаем точки для текущего острова
    loadPoints(currentIslandId);
}

// Обработчик кликов на вкладки
tabButtons.forEach(button => {
    button.addEventListener('click', () => {
        tabButtons.forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');

        const islandName = button.getAttribute('data-island');
        currentIslandId = button.getAttribute('data-id'); // Сохраняем ID текущего острова
        initMap(islandName);
    });
});

// Обработчик клика на карте для добавления точки
mapContainer.addEventListener('click', (event) => {
    if (!map) return;
    const latlng = map.mouseEventToLatLng(event);
    addPoint({ latlng });
});

// Обработчик переключения режима добавления точек
addPointToggle.addEventListener('click', () => {
    isAddingPoints = !isAddingPoints;
    addPointToggle.textContent = isAddingPoints ? 'Отключить добавление точек' : 'Включить добавление точек';
    addPointToggle.classList.toggle('active');
});

// Инициализация карты при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    initMap('erangel');
});