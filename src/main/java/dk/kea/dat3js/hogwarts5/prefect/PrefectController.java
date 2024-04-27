package dk.kea.dat3js.hogwarts5.prefect;

import dk.kea.dat3js.hogwarts5.prefect.PrefectService;
import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prefects")
public class PrefectController {

    private final PrefectService prefectService;

    public PrefectController(PrefectService prefectService) {
        this.prefectService = prefectService;
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<StudentResponseDTO> createPrefect(@PathVariable Integer studentId) {
       return ResponseEntity.status(HttpStatus.CREATED).body(prefectService.create(studentId));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDTO> getPrefect(@PathVariable Integer studentId) {
        return ResponseEntity.ok(prefectService.findStudentIfPrefect(studentId));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllPrefects() {
        return ResponseEntity.ok(prefectService.findAllPrefects());
    }

    @GetMapping("/house/{houseName}")
    public ResponseEntity<List<StudentResponseDTO>> getPrefectsByHouse(@PathVariable String houseName) {
        return ResponseEntity.ok(prefectService.findPrefectsByHouse(houseName));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<StudentResponseDTO> deletePrefect(@PathVariable Integer studentId) {
        return ResponseEntity.of(prefectService.deletePrefect(studentId));
    }
}
