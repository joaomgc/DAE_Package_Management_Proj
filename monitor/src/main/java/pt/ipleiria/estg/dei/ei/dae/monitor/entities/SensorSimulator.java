package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Random;

@Entity
@NamedQuery(name = "getAllSensors", query = "SELECT s FROM SensorSimulator s")
public class SensorSimulator {
    @Id
    private String id;
    private String tipo;
    private LocalDateTime timestamp;
    private double valor;
    private Random random;
    private String status;
    @OneToOne
    @JoinColumn(name = "volume_id")
    private Volume volume;


    public SensorSimulator() {
        // No-arg constructor
    }

     public SensorSimulator(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.status = "Inactive";
        this.random = new Random();
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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

    public void gerarDados() {
        this.timestamp = LocalDateTime.now();
        switch (tipo) {
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
            default:
                throw new IllegalArgumentException("Tipo de sensor desconhecido: " + tipo);
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
