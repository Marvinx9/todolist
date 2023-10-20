package br.com.afraniodantas.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ITaskRepository extends JpaRepository<TaskMoldel, UUID> {
    List<TaskMoldel> findByIdUser(UUID idUser);
}
