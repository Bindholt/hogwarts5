package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.errorhandling.ValidationException;
import dk.kea.dat3js.hogwarts5.house.HouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
  private final StudentRepository studentRepository;
  private final HouseService houseService;

  public StudentService(StudentRepository studentRepository, HouseService houseService) {
    this.studentRepository = studentRepository;
    this.houseService = houseService;
  }

  public List<StudentResponseDTO> findAll() {
    return studentRepository.findAll().stream().map(this::toDTO).toList();
  }

  public Optional<StudentResponseDTO> findById(int id) {
    return studentRepository.findById(id).map(this::toDTO);
  }

  public StudentResponseDTO save(StudentRequestDTO student) {
    return toDTO(studentRepository.save(fromDTO(student)));
  }

  public Optional<StudentResponseDTO> updateIfExists(int id, StudentRequestDTO student) {
    if (studentRepository.existsById(id)) {
      Student existingStudent = fromDTO(student);
      existingStudent.setId(id);
      return Optional.of(toDTO(studentRepository.save(existingStudent)));
    } else {
      // TODO: Throw error?
      return Optional.empty();
    }
  }

  public Optional<StudentResponseDTO> partialUpdate(int id, StudentRequestDTO student) {
    Optional<Student> existingStudent = studentRepository.findById(id);
    if(existingStudent.isPresent()) {
      Student studentToUpdate = existingStudent.get();
      if(student.firstName() != null) {
        studentToUpdate.setFirstName(student.firstName());
      }
      if(student.middleName() != null) {
        studentToUpdate.setMiddleName(student.middleName());
      }
      if(student.lastName() != null) {
        studentToUpdate.setLastName(student.lastName());
      }
      if(student.house() != null) {
        studentToUpdate.setHouse(houseService.findById(student.house()).orElseThrow());
      }
      if(student.schoolYear() != null) {
        studentToUpdate.setSchoolYear(student.schoolYear());
      }
      if(student.isPrefect()) {
        if(checkIfPrefectIsAssignable(studentToUpdate)) {
          studentToUpdate.setPrefect(true);
        } else {
          throw new ValidationException("Prefect is not assignable");
        }
      } else {
        studentToUpdate.setPrefect(false);
      }
      return Optional.of(toDTO(studentRepository.save(studentToUpdate)));
    } else {
      // TODO: handle error
      return Optional.empty();
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

  public Optional<StudentResponseDTO> deleteById(int id) {
    Optional<StudentResponseDTO> existingStudent = studentRepository.findById(id).map(this::toDTO);
    studentRepository.deleteById(id);
    return existingStudent;
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

  private Student fromDTO(StudentRequestDTO studentDTO) {
    Student entity = new Student(
        studentDTO.firstName(),
        studentDTO.middleName(),
        studentDTO.lastName(),
        houseService.findById(studentDTO.house()).orElseThrow(),
        studentDTO.schoolYear(),
        studentDTO.gender(),
        studentDTO.isPrefect()
    );

    if(studentDTO.name() != null) {
      entity.setFullName(studentDTO.name());
    }

    return entity;
  }

  public StudentResponseDTO create(StudentRequestDTO student) {
    return toDTO(studentRepository.save(fromDTO(student)));
  }
}
