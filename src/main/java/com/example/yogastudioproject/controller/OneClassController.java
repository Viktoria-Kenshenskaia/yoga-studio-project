package com.example.yogastudioproject.controller;

import com.example.yogastudioproject.domain.model.OneClass;
import com.example.yogastudioproject.domain.payload.request.ClassToSubscription;
import com.example.yogastudioproject.domain.payload.response.MessageResponse;
import com.example.yogastudioproject.domain.validation.ResponseErrorValidation;
import com.example.yogastudioproject.dto.OneClassDto;
import com.example.yogastudioproject.service.AppUserService;
import com.example.yogastudioproject.service.OneClassService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_ADMIN", "ROLE_MANAGER"})
public class OneClassController {
    private final OneClassService classService;
    private final ModelMapper modelMapper;
    private final ResponseErrorValidation responseErrorValidation;

    @PostMapping("/{teacherId}/create")
    public ResponseEntity<Object> createClass(@Valid @RequestBody OneClassDto classDto,
                                              @PathVariable("teacherId") Long teacherId,
                                              BindingResult bindingResult,
                                              Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        classService.createClass(classDto, teacherId, principal);
        return ResponseEntity.ok(new MessageResponse("Class was created"));
    }

    @PatchMapping("/{classId}/update")
    public ResponseEntity<Object> updateClass(@Valid @RequestBody OneClassDto oneClassDto,
                                              @PathVariable("classId") Long classId,
                                              BindingResult bindingResult,
                                              Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        OneClass oneClass = classService.updateClass(oneClassDto, classId, principal);

        return ResponseEntity.ok().body(modelMapper.map(oneClass, OneClassDto.class));
    }

    @DeleteMapping("/{classId}/delete")
    public ResponseEntity<Object> deleteClass(@PathVariable("classId") Long classId, Principal principal) {
        classService.deleteClass(classId, principal);
        return ResponseEntity.ok().body(new MessageResponse("Class was deleted"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OneClassDto>> getAllClasses(Principal principal) {
        return ResponseEntity.ok().body(classService.getAllClasses(principal)
                .stream().map(oneClass -> modelMapper.map(oneClass, OneClassDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/all/{teacherId}")
    public ResponseEntity<List<OneClassDto>> getAllClassesForTeacher(@PathVariable("teacherId") Long teacherId,
                                                                  Principal principal) {
        return ResponseEntity.ok().body(classService.getAllClassesForTeacher(teacherId, principal)
                .stream().map(oneClass -> modelMapper.map(oneClass, OneClassDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/all/{from}&{to}")
    @RolesAllowed({"ROLE_TEACHER"})
    public ResponseEntity<Object> getAllAfterAndBefore(@PathVariable("from") LocalDateTime classesAfter,
                                                       @PathVariable("to") LocalDateTime classesBefore,
                                                       BindingResult bindingResult,
                                                       Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        return ResponseEntity.ok().body(classService.getAllClassesFromTo(principal, classesAfter, classesBefore));
    }

    @GetMapping("/{classId}/details")
//    @RolesAllowed({"ROLE_TEACHER"})
    public ResponseEntity<Object> getClassDetails(@PathVariable("classId") Long classId,
                                                  Principal principal) {
        OneClass oneClass = classService.getOneClassById(classId, principal);
        return ResponseEntity.ok().body(modelMapper.map(oneClass, OneClassDto.class));
    }

    @PostMapping("/add-to-class")
    public ResponseEntity<Object> addCustomerToClass(@RequestBody ClassToSubscription classToSubscription,
                                                     BindingResult bindingResult,
                                                     Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        classService.addSubscriptionToClass(classToSubscription, principal);
        return ResponseEntity.ok().body(new MessageResponse("Customer was added to subscription!"));
    }

    @DeleteMapping("/remove-from-class")
    public ResponseEntity<Object> removeCustomerFromClass(@RequestBody ClassToSubscription classToSubscription,
                                                          BindingResult bindingResult,
                                                          Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        classService.removeSubscriptionFromClass(classToSubscription, principal);

        return ResponseEntity.ok(new MessageResponse("Customer was removed from class!"));
    }
}