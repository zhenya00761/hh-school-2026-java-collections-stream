package tasks;

import common.Area;
import common.Person;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {

      Map<Integer, String> personMap = new HashMap<>();
      Map<Integer, String> areaMap = new HashMap<>();
      Set<String> result = new HashSet<>();

      for (Person person : persons) {
          personMap.put(person.id(), person.firstName());
      }

      for (Area area : areas) {
          areaMap.put(area.getId(), area.getName());
      }

      for (Map.Entry<Integer, Set<Integer>> entry : personAreaIds.entrySet()) {
          Integer key = entry.getKey();
          Set<Integer> valueSet = entry.getValue();

          for (Integer areaId : valueSet) {
              result.add(personMap.get(key) + " - " + areaMap.get(areaId));
          }
      }

    return result;
  }
}

/*
    Здесь из-за того что в функцию передали Collection<Person> persons и Collection<Area> areas,
    сразу появилась мысль, что можно преобразовать их в HashMap. Это даст сложность О(n ),
    так как нам надо будет пробегать по personAreaIds, а для каждой пары нужно искать Person и Area по id,
    что через HashMap будет выполнятся за константу, но по personAreaIds все равно придется пробежать,
    и по Set<Integer> тоже придется пробежать, что даст сложность О(n).
    Запись уже результата лучше в HashSet, так как firstName и area могут совпасть у разных людей.
 */