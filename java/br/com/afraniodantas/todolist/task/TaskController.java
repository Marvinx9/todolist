package br.com.afraniodantas.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.afraniodantas.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskMoldel taskMoldel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        taskMoldel.setIdUser((UUID) idUser);
        var currentDate = LocalDateTime.now();

        if(currentDate.isAfter(taskMoldel.getStartAt()) || currentDate.isAfter(taskMoldel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("A data de inicio / data de términio devem ser maior que a data atual");
        }

        if(taskMoldel.getStartAt().isAfter(taskMoldel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("A data de início deve ser menor que a data de término");
        }
        var task = this.taskRepository.save(taskMoldel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public  List<TaskMoldel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }
    
    @PutMapping("/{id}")
    public TaskMoldel update(@RequestBody TaskMoldel taskMoldel, HttpServletRequest request, @PathVariable UUID id) {
        var idUser = request.getAttribute("idUser");

        var task = this.taskRepository.findById(id).orElse(null);

        Utils.copyNonNullProperties(taskMoldel, task);

        
        return this.taskRepository.save(task);
    }
}
