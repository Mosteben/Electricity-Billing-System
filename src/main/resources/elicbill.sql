
CREATE DATABASE electricit;

USE electricit;


CREATE TABLE customers (
    meter_code VARCHAR(20) PRIMARY KEY,
    name NVARCHAR(100),
    email NVARCHAR(100),
    type VARCHAR(10),
    previous_reading FLOAT,
    current_reading FLOAT,
    property_details NVARCHAR(MAX),
    contract_attachment NVARCHAR(MAX)
);
ALTER TABLE customers
ADD password NVARCHAR(100);


CREATE TABLE users (
    username NVARCHAR(50) PRIMARY KEY,
    role VARCHAR(20),
    password NVARCHAR(100)
);



CREATE TABLE payments (
    payment_id INT IDENTITY(1,1) PRIMARY KEY,
    meter_code VARCHAR(20),
    amount FLOAT,
    payment_date DATE,
    FOREIGN KEY (meter_code) REFERENCES customers(meter_code)
);

CREATE TABLE complaints (
    complaint_id INT IDENTITY(1,1) PRIMARY KEY,
    meter_code VARCHAR(20),
    complaint_text NVARCHAR(MAX),
    complaint_date DATE,
    FOREIGN KEY (meter_code) REFERENCES customers(meter_code)
);


CREATE TABLE bills (
    bill_id INT IDENTITY(1,1) PRIMARY KEY,
    meter_code VARCHAR(20),
    bill_date DATE,
    amount_due FLOAT,
    amount_paid FLOAT,
    due_date DATE,
    payment_status VARCHAR(20),
    FOREIGN KEY (meter_code) REFERENCES customers(meter_code)
);
CREATE TABLE tariffs (
    tariff_id INT IDENTITY(1,1) PRIMARY KEY,
    tier1_rate FLOAT NOT NULL,
    tier2_rate FLOAT NOT NULL,
    tier3_rate FLOAT NOT NULL,
    tier4_rate FLOAT NOT NULL,
    tier5_rate FLOAT NOT NULL,
    tier6_rate FLOAT NOT NULL,
    service_fee1 FLOAT NOT NULL,
    service_fee2 FLOAT NOT NULL,
    service_fee3 FLOAT NOT NULL,
    service_fee4 FLOAT NOT NULL,
    service_fee5 FLOAT NOT NULL,
    effective_date DATE NOT NULL
);

-- Insert a sample row with tariff rates and fees
INSERT INTO tariffs (tier1_rate, tier2_rate, tier3_rate, tier4_rate, tier5_rate, tier6_rate, service_fee1, service_fee2, service_fee3, service_fee4, service_fee5, effective_date)
VALUES (0.48, 0.58, 0.77, 1.06, 1.28, 1.45, 2, 11, 15, 25, 40, GETDATE());

CREATE TABLE meter_status (
    meter_code VARCHAR(20) PRIMARY KEY,
    status VARCHAR(20) DEFAULT 'active',
    last_updated DATE,
    FOREIGN KEY (meter_code) REFERENCES customers(meter_code)
);

ALTER TABLE customers
ADD cancellation_date DATE;

ALTER TABLE customers
ADD subscription_status VARCHAR(20) DEFAULT 'Active'; 



UPDATE customers
SET subscription_status = 'Active'
WHERE LOWER(subscription_status) = 'active';






-- Check if old customers have corresponding bills
SELECT c.meter_code, c.previous_reading, c.current_reading
FROM customers c
LEFT JOIN bills b ON c.meter_code = b.meter_code
WHERE c.type = 'old';


SELECT c.meter_code, b.bill_date, b.due_date, b.payment_status, c.previous_reading, c.current_reading
FROM bills b
JOIN customers c ON b.meter_code = c.meter_code
WHERE c.type = 'old' 
  AND b.region = 'Giza' 
  --AND b.payment_status IN ('unpaid', 'partially_paid');

SELECT  * from customers
SELECT  * from users

  SELECT c.meter_code, b.bill_date, b.due_date, b.payment_status, c.previous_reading, c.current_reading
FROM bills b
JOIN customers c ON b.meter_code = c.meter_code
--WHERE c.type = 'New'
AND b.region = 'Aswan'
AND b.payment_status <> 'Paid'
AND c.subscription_status = 'Inactive';
