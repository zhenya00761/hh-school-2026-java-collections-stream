package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {

      return Stream.of(persons1, persons2)
              .flatMap(collection -> collection.stream())
              .sorted(Comparator.comparing(Person::createdAt))
              .limit(limit)
              .collect(Collectors.toList());

  }
}

/*
    Я вижу тут 2 решения, через Array List или через Stream API.
    Если делать по 1 варианту, то получается довольно много промежуточных простых строк и надо делать compare.
    Я больше захотел сделать через Stream API, так код будет более легче для понимания.
    Также на лекции я не особо понял эту тему, поэтому решил разобраться.

    Главной проблемой для меня стало объединение persons1 и persons2.
    Понял, что можно создать поток, тип данных которых являются коллекции, и поместить в него persons1 и persons2,
    а потом через flatMap сделать для каждой коллекции поток, где будут все люди.
    А flatMap объеденит эти потоки, что позволит удобно работать уже в одном потоке со всеми элементами persons1 и persons2.
 */