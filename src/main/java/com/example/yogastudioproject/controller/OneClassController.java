package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.OneClass;
import com.example.yogastudioproject.dto.OneClassDto;
import com.example.yogastudioproject.service.AppUserService;
import com.example.yogastudioproject.service.OneClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_ADMIN"})
public class OneClassController {
    private final OneClassService classService;
    private final AppUserService userService;

    @PostMapping("/create")
    public ResponseEntity<OneClass> createClass(@Valid @RequestBody OneClassDto classDto) {
      return   ResponseEntity.ok().body(classService.createClass(classDto));
    }

    @PostMapping("/update")
    public ResponseEntity<OneClass> updateClass(@Valid @RequestBody OneClassDto classDto,
                                                @RequestBody Long classId) {
        return   ResponseEntity.ok().body(classService.updateClass(classDto, classId));
    }

    @DeleteMapping("/delete")
    public void deleteClass(@RequestBody Long classId) {

        classService.deleteClassById(classId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OneClass>> getAllClasses(Principal principal) {
        return ResponseEntity.ok().body(classService.getAllClasses(principal));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<OneClass>> getAllClassesForTeacher(@PathVariable("id") Long teacherId) {
        return ResponseEntity.ok().body(classService.getAllClassesForTeacherById(teacherId));
    }

    @GetMapping("/all/{from}&{to}")
    public ResponseEntity<List<OneClass>> getAllFromTo(@PathVariable("from") LocalDateTime from,
                                                       @PathVariable("to") LocalDateTime to,
                                                       Principal principal) {
        return ResponseEntity.ok().body(classService.getAllClassesFromTo(principal, from, to));
    }

}
