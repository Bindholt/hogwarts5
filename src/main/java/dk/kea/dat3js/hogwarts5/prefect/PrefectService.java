package dk.kea.dat3js.hogwarts5.prefect;

import dk.kea.dat3js.hogwarts5.errorhandling.ValidationException;
import dk.kea.dat3js.hogwarts5.house.HouseService;
import dk.kea.dat3js.hogwarts5.students.Student;
import dk.kea.dat3js.hogwarts5.students.StudentRepository;
import dk.kea.dat3js.hogwarts5.students.StudentRequestDTO;
import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrefectService {
    private final StudentRepository studentRepository;
    private final HouseService houseService;

    public PrefectService(StudentRepository studentRepository, HouseService houseService) {
        this.studentRepository = studentRepository;
        this.houseService = houseService;
    }

    public StudentResponseDTO create(Integer studentId) {
        Student newPrefect = studentRepository.findById(studentId).orElseThrow();
        if(checkIfPrefectIsAssignable(newPrefect)) {
            newPrefect.setPrefect(true);
            return toDTO(studentRepository.save(newPrefect));
        } else {
            throw new ValidationException("Prefect is not assignable");
        }
    }

    private boolean checkIfPrefectIsAssignable(Student newPrefect) {
        List<Student> prefectsInSameHouse = studentRepository.findAll().stream()
                .filter(s -> s.getHouse().equals(newPrefect.getHouse()) && s.isPrefect())
                .toList();

        if(prefectsInSameHouse.size() >= 2) {
            return false;
        }

        if(prefectsInSameHouse.size() == 1) {
            if (prefectsInSameHouse.getFirst().getGender().equals(newPrefect.getGender())) {
                return false;
            }
        }

        return newPrefect.getSchoolYear() >= 5;
    }

    private StudentResponseDTO toDTO(Student studentEntity) {
        StudentResponseDTO dto = new StudentResponseDTO(
                studentEntity.getId(),
                studentEntity.getFirstName(),
                studentEntity.getMiddleName(),
                studentEntity.getLastName(),
                studentEntity.getFullName(),
                studentEntity.getHouse().getName(),
                studentEntity.getSchoolYear(),
                studentEntity.getGender(),
                studentEntity.isPrefect()
        );

        return dto;
    }

    public StudentResponseDTO findStudentIfPrefect(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        if(student.isPrefect()) {
            return toDTO(student);
        } else {
            throw new ValidationException("Student is not a prefect");
        }
    }

    public List<StudentResponseDTO> findAllPrefects() {
        return studentRepository.findAll().stream()
                .filter(Student::isPrefect)
                .map(this::toDTO)
                .toList();
    }

    public List<StudentResponseDTO> findPrefectsByHouse(String houseName) {
        return studentRepository.findAll().stream()
                .filter(s -> s.getHouse().getName().equals(houseName) && s.isPrefect())
                .map(this::toDTO)
                .toList();
    }

    public Optional<StudentResponseDTO> deletePrefect(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        if(student.isPrefect()) {
            student.setPrefect(false);
            return Optional.of(toDTO(studentRepository.save(student)));
        } else {
            return Optional.empty();
        }
    }
}
