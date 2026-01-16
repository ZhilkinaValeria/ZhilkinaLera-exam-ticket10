-- Создание таблицы для сырых событий платежей
CREATE TABLE IF NOT EXISTS сырые_события_платежей (
    идентификатор BIGSERIAL PRIMARY KEY,
    фио_плательщика VARCHAR(255) NOT NULL,
    сумма DECIMAL(15, 2) NOT NULL,
    валюта VARCHAR(10) NOT NULL,
    способ_оплаты VARCHAR(50) NOT NULL,
    дата_события TIMESTAMP NOT NULL,
    дата_создания TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Создание индексов для оптимизации запросов
CREATE INDEX IF NOT EXISTS idx_event_date ON сырые_события_платежей (дата_события);
CREATE INDEX IF NOT EXISTS idx_created_at ON сырые_события_платежей (дата_создания);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE сырые_события_платежей IS 'Таблица для хранения сырых событий платежей';
COMMENT ON COLUMN сырые_события_платежей.идентификатор IS 'Уникальный идентификатор записи';
COMMENT ON COLUMN сырые_события_платежей.фио_плательщика IS 'ФИО плательщика';
COMMENT ON COLUMN сырые_события_платежей.сумма IS 'Сумма платежа';
COMMENT ON COLUMN сырые_события_платежей.валюта IS 'Валюта платежа (RUB, USD, EUR, etc.)';
COMMENT ON COLUMN сырые_события_платежей.способ_оплаты IS 'Способ оплаты (карта, наличные, онлайн и т.д.)';
COMMENT ON COLUMN сырые_события_платежей.дата_события IS 'Дата и время события платежа';
COMMENT ON COLUMN сырые_события_платежей.дата_создания IS 'Дата и время создания записи в БД';