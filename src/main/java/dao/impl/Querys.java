package dao.impl;

public class Querys {
    private Querys() {
    }
    public static final String INSERT_INTO_CUSTOMER_VALUES = "INSERT INTO Customer VALUES (?, ?, ?, ?, ?,?)";
    public static final String UPDATE_CUSTOMER_SET_NAME_LAST_NAME_DOB_PHONE_MAIL_WHERE_ID = "UPDATE Customer SET Name = ?, LastName = ?, dob = ?, phone = ?, Mail = ? WHERE Id = ?";
    public static final String SELECT_FROM_CUSTOMER = "SELECT * FROM Customer";
    public static final String UPDATE_CREDENTIALS_SET_PASSWORD_ACTIVADO_FECHA_ACTIVACION_CODIGO_ACTIVACION_ROL_WHERE_EMAIL = "UPDATE Credentials SET password = ?, activado = ?, fechaActivacion = ?, codigoActivacion = ?, rol = ? WHERE email = ?";
    public static final String SELECT_FROM_CREDENTIALS_WHERE_CODIGO_ACTIVACION = "SELECT * FROM Credentials WHERE codigoActivacion = ?";
    public static final String UPDATE_CREDENTIALS_SET_CODIGO_ACTIVACION_WHERE_EMAIL = "UPDATE Credentials SET codigoActivacion = ? WHERE email = ?";
    public static final String UPDATE_CREDENTIALS_SET_TEMPORAL_PASSWORD_WHERE_EMAIL = "UPDATE Credentials SET temporalPassword = ? WHERE email = ?";
    public static final String SELECT_FROM_CREDENTIALS_WHERE_EMAIL = "SELECT * FROM Credentials WHERE email = ?";
    public static final String UPDATE_CREDENTIALS_SET_REFRESH_TOKEN_WHERE_EMAIL = "UPDATE Credentials SET refreshToken = ? WHERE email = ?";
    public static final String SELECT_FROM_CREDENTIALS_WHERE_REFRESH_TOKEN = "SELECT * FROM Credentials WHERE refreshToken = ?";
    public static final String UPDATE_CREDENTIALS_SET_ACCESS_TOKEN_WHERE_EMAIL = "UPDATE Credentials SET accessToken = ? WHERE email = ?";
    public static final String SELECT_FROM_CREDENTIALS_WHERE_ACCESS_TOKEN = "SELECT * FROM Credentials WHERE accessToken = ?";
    public static final String UPDATE_CREDENTIALS_SET_PASSWORD_WHERE_EMAIL = "UPDATE Credentials SET password = ?, SET temporalpassword = ? WHERE email = ?";
    public static final String SELECT_FROM_ORDERS = "SELECT * FROM orders";
    public static final String INSERT_INTO_ORDERS_ID_CUSTOMER_ID_ORDER_DATE_TABLE_ID_VALUES = "INSERT INTO orders(Id, customerId, orderDate, tableId) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_ORDERS_SET_CUSTOMER_ID_ORDER_DATE_TABLE_ID_WHERE_ID = "UPDATE orders SET customerId = ?, orderDate = ?, tableId = ? WHERE Id = ?";
    public static final String DELETE_FROM_ORDERS_WHERE_ID = "DELETE FROM orders WHERE Id = ?";
    public static final String SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID = "SELECT * FROM orders WHERE customerId = ?";
}
