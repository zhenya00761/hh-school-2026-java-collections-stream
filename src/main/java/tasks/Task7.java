package tasks;

import common.Company;
import common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  public static Set<String> vacancyNames(Collection<Company> companies) {

      return companies.stream()
          .flatMap(company -> company.getVacancies().stream())
          .map(Vacancy::getTitle)
          .collect(Collectors.toSet());
  }

}

/*
    Здесь я решил делать через Stream API,
    потому что можно удобно и легко (по коду) собрать все Set<Vacancy> для каждого элемента из Collection<Company> companies.
    Идея пришла из второй задачи, передав в поток список companies через .flatMap можно пробежаться по каждому Set<Vacancy>
    для каждой компании, создав отдельный поток, где будут уже все Vacancy vacancy. Потом надо только преобразовать из каждой вакансии
    в соответсвии их tittle и объеденить в HashSet.
 */