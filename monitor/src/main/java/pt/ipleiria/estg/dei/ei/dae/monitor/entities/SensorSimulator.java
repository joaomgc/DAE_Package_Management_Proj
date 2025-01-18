package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

@Entity
@NamedQuery(name = "getAllSensors", query = "SELECT s FROM SensorSimulator s")
public class SensorSimulator {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "tipo_sensor_id", nullable = false)
    private TipoSensor tipo;

    private LocalDateTime timestamp;
    private double valor;
    private Random random;
    private String status;
    @OneToOne
    @JoinColumn(name = "volume_id")
    private Volume volume;


    public SensorSimulator() {}

    public SensorSimulator(String id, TipoSensor tipo, String status) {
        this.id = id;
        this.tipo = tipo;
        this.status = status;
        this.random = new Random();
    }


    public String getId() {
        return id;
    }

    public TipoSensor getTipo() {
        return tipo;
    }

    public void setTipo(TipoSensor tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void gerarDados() {
        this.timestamp = LocalDateTime.now();
        switch (tipo.getNome().toLowerCase()) {
            case "temperatura":
                this.valor = -10 + (40 - (-10)) * random.nextDouble(); // Gera valores entre -10 e 40 graus Celsius
                break;
            case "pressao":
                this.valor = 950 + (1050 - 950) * random.nextDouble(); // Gera valores entre 950 e 1050 hPa
                break;
            case "aceleracao":
                this.valor = random.nextDouble() * 10; // Gera valores entre 0 e 10 g
                break;
            case "posicionamento":
                this.valor = random.nextDouble() * 100; // Gera valores fictícios para simulação
                break;
            case "luminosidade":
                this.valor = random.nextDouble() * 100;
                break;
            default:
                this.valor = 0.0;
                System.out.println("Unknown sensor type: " + tipo.getNome());
        }
    }

    @Override
    public String toString() {
        return "SensorSimulator{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", timestamp=" + timestamp +
                ", valor=" + valor +
                ", status=" + status +
                '}';
    }
}
