package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Задача 5
Расширим предыдущую задачу.
Есть список персон, и словарь сопоставляющий id каждой персоны и id региона
Необходимо выдать список персон ApiPersonDto, с правильно проставленными areaId
Конвертер одной персоны дополнен!
 */
public class Task5 {

  private final PersonConverter personConverter;

  public Task5(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  public List<ApiPersonDto> convert(List<Person> persons, Map<Integer, Integer> personAreaIds) {
      List<ApiPersonDto> personsDto = new ArrayList<>();

      for (Person person : persons) {
          Integer areaId = personAreaIds.get(person.id());
          ApiPersonDto personDto = personConverter.convert(person, areaId);
          personsDto.add(personDto);
      }

      return personsDto;
  }
}

/*
    Тут немного не понял, что лучше Stream API или простой перебор,
    но посчитал что так будет удобнее и понятнее, так как если делать через Stream API,
    то пришлось бы обращаться к personAreaIds (чтобы найти person.id())
    в .map() и прямо в этой команде конвертировать в ApiPersonDto
    (да и не особо понял как это сделать)
 */