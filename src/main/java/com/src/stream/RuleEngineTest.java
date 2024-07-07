package com.src.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

interface TestRule{
    public <T> Predicate<T> getPredicate();
}

enum Rules implements TestRule{
    AGE_GREATER_THAN_30(person -> person.getAge()>30),
    NAME_STARTS_WITH_B(person -> person.getName().startsWith("B"));

    private final Predicate<Person> predicate;

    Rules(Predicate<Person> predicate){
        this.predicate = predicate;
    }

    @Override
    public Predicate<Person> getPredicate() {
        return predicate;
    }
}

class RuleEngine<T>{
    private List<TestRule> rules;

    public RuleEngine(){
        rules = new ArrayList<>();
    }

    public void addRule(TestRule testRule){
        rules.add(testRule);
    }

    public List<T> filterItems(List<T> items){
        List<T> filterItems = new ArrayList<>();
        items.forEach(item -> {
            if(rules.stream().allMatch(rule -> rule.getPredicate().test(item))){
                filterItems.add(item);
            }
        });
        return filterItems;
    }
}

public class RuleEngineTest{

    public static void main(String[] args) {
        List<Person> personList = List.of(
                new Person("Paul", 24, 20000 ,"Admin"),
                new Person("Mark", 30, 30000, "Finance"),
                new Person("Will", 28, 28000, "IT"),
                new Person("Billiam", 39, 28000, "Admin"),
                new Person("Will", 28, 30000, "Finance"));

        RuleEngine<Person> ruleEngine = new RuleEngine<>();
        ruleEngine.addRule(Rules.AGE_GREATER_THAN_30);
        ruleEngine.addRule(Rules.NAME_STARTS_WITH_B);

        List<Person> filteredPeople = ruleEngine.filterItems(personList);
        for (Person person : filteredPeople) {
            System.out.println(person.getName());
        }
    }
}


