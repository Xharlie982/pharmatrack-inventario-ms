-- =============== PostgreSQL: Inventario & Sucursales (DDL) ===============
-- Crear TYPE para movimientos
DO $$ BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'mov_tipo') THEN
    CREATE TYPE mov_tipo AS ENUM ('IN','OUT','ADJ');
  END IF;
END $$;

-- Sucursales
CREATE TABLE IF NOT EXISTS sucursal (
  id_sucursal      SERIAL PRIMARY KEY,
  nombre           VARCHAR(80)  NOT NULL,
  distrito         VARCHAR(60)  NOT NULL,
  direccion        VARCHAR(120),
  lat              NUMERIC(9,6),
  lon              NUMERIC(9,6),
  estado           VARCHAR(16)  NOT NULL DEFAULT 'ACTIVA'
                   CHECK (estado IN ('ACTIVA','INACTIVA'))
);

-- Stock por sucursal x producto (producto viene de Catálogo/Mongo)
CREATE TABLE IF NOT EXISTS producto_stock (
  id_sucursal      INT NOT NULL REFERENCES sucursal(id_sucursal) ON DELETE CASCADE,
  id_producto      VARCHAR(64) NOT NULL,
  stock_disponible INT NOT NULL DEFAULT 0 CHECK (stock_disponible >= 0),
  pto_reorden      INT NOT NULL DEFAULT 10 CHECK (pto_reorden >= 0),
  updated_at       TIMESTAMP   NOT NULL DEFAULT now(),
  CONSTRAINT pk_producto_stock PRIMARY KEY (id_sucursal, id_producto)
);

-- Movimientos de stock
CREATE TABLE IF NOT EXISTS movimiento_stock (
  id               BIGSERIAL PRIMARY KEY,
  id_sucursal      INT NOT NULL REFERENCES sucursal(id_sucursal) ON DELETE CASCADE,
  id_producto      VARCHAR(64) NOT NULL,
  tipo             mov_tipo    NOT NULL,
  cantidad         INT         NOT NULL CHECK (cantidad > 0),
  motivo           VARCHAR(120),
  ref_documento    VARCHAR(60),
  created_at       TIMESTAMP   NOT NULL DEFAULT now()
);

-- Índices recomendados
CREATE INDEX IF NOT EXISTS idx_stock_producto
  ON producto_stock (id_producto);

CREATE INDEX IF NOT EXISTS idx_mov_busqueda
  ON movimiento_stock (id_sucursal, id_producto, created_at);

-- Trigger: actualizar updated_at en producto_stock
CREATE OR REPLACE FUNCTION set_updated_at() RETURNS trigger AS $$
BEGIN
  NEW.updated_at := now();
  RETURN NEW;
END; $$ LANGUAGE plpgsql;

DO $$ BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_trigger WHERE tgname = 'trg_producto_stock_updated_at'
  ) THEN
    CREATE TRIGGER trg_producto_stock_updated_at
    BEFORE UPDATE ON producto_stock
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();
  END IF;
END $$;
