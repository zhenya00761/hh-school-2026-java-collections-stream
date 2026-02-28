package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {

      Map<Integer, Set<Resume>> personMap = new HashMap<>();
      Set<PersonWithResumes> personWithResumes = new HashSet<>();

      for (Person person : persons) {
          personMap.put(person.id(), new HashSet<Resume>());
      }

      Set<Integer> personsId = persons.stream()
              .map(person -> person.id())
              .collect(Collectors.toSet());

    Set<Resume> resumes = personService.findResumes(personsId);

      for (Resume resume : resumes) {
          Set<Resume> resumesPerson = personMap.get(resume.personId());
          resumesPerson.add(resume);
      }

      for (Person person : persons) {
          personWithResumes.add(new PersonWithResumes(person, personMap.get(person.id())));
      }

    return personWithResumes;
  }
}

/*
    В этой задаче у меня была основная идея, что итеррировать каждый элемент из persons и для каждого искать подходящий
    по id элемент из resumes очень долго. Лучше всего проитерировать persons и resumes отдельно, чтобы после пробега по persons
    можно всего один раз пробежаться по resumes, для этого подойдет HashMap. Так будет оптимальная сложность O(n).
 */