package jakarta.errores;

public class ErrorApp {
        private final String mensaje;
        public ErrorApp(String mensaje) {
            this.mensaje = mensaje;
        }
        public String getMensaje() {
            return mensaje;
        }
}
