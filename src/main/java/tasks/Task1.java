package tasks;

import common.Person;
import common.PersonService;

import java.util.*;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    Map<Integer, Person> personMap = new HashMap<>();
    for (Person person : persons) {
        personMap.put(person.id(), person);
    }

    List<Person> result = new ArrayList<>();
    for (int id : personIds) {
        result.add(personMap.get(id));
    }

    return result;
  }
}

/*
Сложность: O(n)
 */
/*
Для каждой задачи я буду оставлять такие комментарии в конце,
чтобы вы могли быстро понять мои ошибки в логике (я понимаю, что в реальных проектах так лучше не писать,
так я пишу только ради того, чтобы я лучше понял в чем еще стоит разобраться исходя из вашей оценки во время обучения)

    Сначала думал над реализацией через LinkedHashMap, так как в нем порядок при итерировании = порядку добавления,
    но для этой задачи не требуется сохранения в нужном порядке, а только вывод в порядке id из personIds,
    поэтому использование HashMap будет достаточным и оптимальным.
    LinkedHashMap использовал бы больше памяти и медленее работал из-за двунаправленных связей между элементами,
    тут опять побеждает HashMap (при большом количестве данных тратил бы в разы больше ресурсов).

    Для сохранения person в порядке по id из personIds был выбран ArrayList,
    так как добавление в конец проходит за константу (O(1)) и никакиз особенных методов использовать не надо.
 */