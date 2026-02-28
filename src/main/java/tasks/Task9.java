package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  /*
  Переменная count используется только в countEven, и то обнуляется и работает как счетчик,
  поэтому ее хранение бессмысленно
  Также уже работая с функцией countEven заметил, что она меняет эту переменную каждый раз,
  когда вызывается (count = 0), что уже противоречит инкапсуляции
   */

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  /*
  В данной функции была замена по сути для более удобного чтения кода и логики,
  return Collections.emptyList(); тоже думал изменить на List.of();,
  но если поменять, то мы выдаем изменяемый List, что может вызвать в будущем ошибки,
  если кто-то захочет добавить в этот List что-то и сможет это сделать
   */
  public List<String> getNames(List<Person> persons) {
    if (persons.isEmpty()) {
      return Collections.emptyList();
    }

    /*
    Заменил persons.remove(0) на .skip(1), так как нам необходимо именно конвертировать,
    начиная со второй, а persons.remove(0); полностью удалит первый элемент,
    а значит потеряем этот объект, что неправильно, так как нам нет необходимости его удалять
    А .skip(1) просто пропустит этот элемент при чтении
     */
    return persons.stream()
        .skip(1)
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  /*
  Сама Java предложила поменять на этот вариант преобразования к Set,
  я не вижу ничего плохого в этом преобразовании,
  так как изначальное преобразование более громоздкое
  Нам не надо использовать .stream(), чтобы выполнить одну терминальную операцию
  Также .distinct() бессмысленный, так как нам и так надо вернуть Set, где каждый элемент не повторяется,
  .distinct() делает тоже самое
   */
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    String result = "";
    if (person.secondName() != null) {
      result += person.secondName();
    }

    if (person.firstName() != null) {
      result += " " + person.firstName();
    }

    /*
    Замена, чтобы возвращать правильно ФИО
     */
    if (person.middleName() != null) {
      result += " " + person.middleName();
    }
    return result;
  }

  /*
  Убрал initialCapacity:1, так как из-за этого параметра при добавлении первого же элемента будет ресайз
  (потом также они будут часто применятся), что будет критически влияеть на скорость этой функции.
  Можно поменять этот цикл на реализацию через Stream Api, но это ни на что не повлияет, кроме того
  поток будет состоять только из одной терминальной комманды, что не будет хорошим решением
   */
  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {

    Map<Integer, String> map = new HashMap<>();
    for (Person person : persons) {
      if (!map.containsKey(person.id())) {
        map.put(person.id(), convertPersonToString(person));
      }
    }
    return map;
  }

  /*
  Изначально очень низкая производительность O(n^2) для передаваемых в функцию Collection.
  Для того, чтобы проверить совпаданеия в двух коллекциях, лучше использовать интерфейс Set,
  который уберет все одинаковые элементы (заранее неизвестно, могут ли повторяться элементы
  в изначальных коллекциях, но нам надо сравнивать единственный экземпляр элемента, с элементами другой коллекции,
  поэтому потери данных не произойдет).
  Благодаря работе HashSet сложность будет O(n), что уже лучше чем было. Также из функции возвращается
  true сразу после нахождения одинаковых элементов, чего не было в исходном коде.
   */
  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set <Person> personSet = new HashSet<>(persons1);

    for (Person person: persons2){
      if (personSet.contains(person)){
        return true;
      }
    }

    return false;
  }

  /*
  Главная ошибка этого метода описана на строках 24-29.
  Также вместо .forEach, можно использовать .count, что просто проще и понятнее,
  так как есть этот метод проще и понятнее, а также быстрее чем .forEach
   */
  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  /*
  Мы создаем HashSet из элементов integers, которые были перетасованы,
  но HashCode() работает так, что при добавлении в HashSet чисел (размер будет степень двойки, поэтому для числа
  10000 размер будет 16384, что дает уникальный HashCode для каждого элемента),
  их HashCode совпадает с самим числом (для Integer), следовательно,
  когда итератор пробегает в set.toString(), строка будет одинаковая каждый раз
   */
  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream
        .rangeClosed(1, 10000)
        .boxed()
        .collect(Collectors.toList());

    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
