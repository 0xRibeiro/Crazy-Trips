package horas.crazytrips.model;

public class ViagemFactory {
    public static Viagem criarViagem(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("Tipo de viagem não pode ser nulo ou vazio");
        }

        return switch (tipo.toLowerCase()) {
            case "nacional" -> new ViagemNacional();
            case "internacional" -> new ViagemInternacional();
            default -> throw new IllegalArgumentException("Tipo de viagem inválido: " + tipo);
        };
    }
}