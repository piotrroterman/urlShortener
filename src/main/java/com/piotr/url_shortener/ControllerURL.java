package com.piotr.url_shortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
class ControllerURL {

    private final LinkServiceImpl service;

    public ControllerURL(LinkServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/info/{shortenedLink}")
    public ResponseEntity getOne(@PathVariable UUID shortenedLink) throws NoSuchElementException {
        return new ResponseEntity<Link>(UtilityClass.checkFound(service.findById(shortenedLink)), HttpStatus.OK);
    }

    @GetMapping("/{shortenedLink}")
    public RedirectView redirect(@PathVariable UUID shortenedLink) {
        return new RedirectView(service.findById(shortenedLink).getOriginalLink());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Link>> getAll() {
        return new ResponseEntity<List<Link>>(service.getAllLink(), HttpStatus.OK);
    }

    @PostMapping("/saveLink")
    public ResponseEntity<Link> createLink(@RequestBody Link link) {
        return new ResponseEntity<Link>(service.createLink(link), HttpStatus.CREATED);
    }

    @DeleteMapping("/{shortenedLink}")
    public ResponseEntity deleteOne(@PathVariable UUID shortenedLink) {
        service.deleteLink(shortenedLink);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteOutdated")
    public ResponseEntity deleteOld() {
        service.deleteOldRecords(Instant.now().minusSeconds(10));
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void handleNoSuchElementException() {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException() {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public void ObjectOptimisticLockingFailureException() {
    }
}
