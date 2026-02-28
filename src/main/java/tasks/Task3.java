package tasks;

import common.Person;

import java.util.*;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 implements Comparator<Person>{

  public static List<Person> sort(Collection<Person> persons) {

      List<Person> personList = new ArrayList<>(persons);

      personList.sort(new Task3());

      return personList;
  }

  @Override
  public int compare(Person person1, Person person2){
      int secondName = person1.secondName().compareTo(person2.secondName());
      if (secondName != 0) {
          return secondName;
      }

      int firstName = person1.firstName().compareTo(person2.firstName());
      if (firstName != 0) {
          return firstName;
      }

      return person1.createdAt().compareTo(person2.createdAt());
  }
}


/*
    В этой задаче не было особых проблем, кроме применение compare.
    Сначала я применил sort к Collection<Person> persons и получил ошибку.
    Почитал документацию и понял, что в интерфейсе Collection нет метода sort,
    и что от Collection также наследуются Set и Queue, в которых тоже нет sort.
    Поэтому самым логичным вариантом было преобразовать в List и его уже отсортировать.
 */