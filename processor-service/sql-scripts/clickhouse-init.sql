-- Создание базы данных, если не существует
CREATE DATABASE IF NOT EXISTS agg_db;

-- Использование базы данных
USE agg_db;

-- Создание таблицы для агрегатов событий платежей
CREATE TABLE IF NOT EXISTS агрегаты_событий_платежей (
    дата_и_время_записи DateTime DEFAULT now(),
    количество_записей UInt64
) ENGINE = MergeTree()
ORDER BY дата_и_время_записи
SETTINGS index_granularity = 8192;

-- Комментарии к таблице
COMMENT ON TABLE агрегаты_событий_платежей IS 'Таблица для хранения агрегированных данных о количестве событий платежей';
COMMENT ON COLUMN агрегаты_событий_платежей.дата_и_время_записи IS 'Дата и время записи агрегата';
COMMENT ON COLUMN агрегаты_событий_платежей.количество_записей IS 'Количество записей в PostgreSQL на момент агрегации';

-- Создание материализованного представления для ежедневной статистики (опционально)
CREATE TABLE IF NOT EXISTS daily_aggregates (
    date Date,
    total_records AggregateFunction(sum, UInt64)
) ENGINE = AggregatingMergeTree()
ORDER BY date;

CREATE MATERIALIZED VIEW IF NOT EXISTS daily_aggregates_mv TO daily_aggregates AS
SELECT
    toDate(дата_и_время_записи) as date,
    sumState(количество_записей) as total_records
FROM агрегаты_событий_платежей
GROUP BY date;