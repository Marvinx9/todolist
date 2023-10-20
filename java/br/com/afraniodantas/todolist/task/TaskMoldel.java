package br.com.afraniodantas.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;

/**
     * ID
     * Usuário (ID usuario)
     * Descrição
     * Título
     * Data de Início
     * Data de Término
     * Prioridade
     */
@Data
@Entity(name = "tb_tasks")
public class TaskMoldel {
    
    @Id 
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
    
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private UUID idUser;
    
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
