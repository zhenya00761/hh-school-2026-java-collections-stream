package tasks;

import common.Person;

import java.util.*;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3{

  public static List<Person> sort(Collection<Person> persons) {
      return persons.stream()
          .sorted(Comparator.comparing(Person::secondName)
              .thenComparing(Person::firstName)
              .thenComparing(Person::createdAt))
          .toList();
  }

}


/*
    В этой задаче не было особых проблем, кроме применение compare.
    Сначала я применил sort к Collection<Person> persons и получил ошибку.
    Почитал документацию и понял, что в интерфейсе Collection нет метода sort,
    и что от Collection также наследуются Set и Queue, в которых тоже нет sort.
    Поэтому самым логичным вариантом было преобразовать в List и его уже отсортировать.
 */