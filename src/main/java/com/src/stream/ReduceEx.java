package com.src.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.src.stream.CollectorEx.getAllServicePrices;
import static com.src.stream.ListEx.getTaxationDetailsList;

public class ReduceEx {

    public static void main(String[] args) {

        //All elements follow rule
        SICExceptionTrace sic1 = new SICExceptionTrace("Success","200 OK");
        SICExceptionTrace sic2 = new SICExceptionTrace("Success","200 OK");
        SICExceptionTrace sic3 = new SICExceptionTrace("Failure","404 Not OK");
        List<SICExceptionTrace> sicExceptionTraces = Arrays.asList(sic1,sic2,sic3);
        boolean allSuccess = sicExceptionTraces.stream()
                .map(sicException -> sicException.getResponseCode().equalsIgnoreCase("Success"))
                .reduce(Boolean::logicalAnd)
                .orElse(false);
        System.out.println("allSuccess :: " + allSuccess); //false

        //Rule Engine
        Predicate<Employee1> predicate1 = employee -> employee.getName().startsWith("K");
        Predicate<Employee1> predicate2 = employee -> employee.getName().startsWith("A");
        List<Predicate<Employee1>> predicates = List.of(predicate1, predicate2);
        List<Employee1> employeeList = EmployeeStream.getEmployeeList();

        //filter out employees with names starting with A and K
        List<Employee1> employeesKA = employeeList.stream().filter(employee -> predicates.stream().anyMatch(predicate -> predicate.test(employee))).toList();
        System.out.println("employeesKA List---------");
        employeesKA.forEach(emp -> System.out.println(emp.getName() + "-" + emp.getSalary()));

        //check if all employees start with name A or K
        boolean allEmpsNameStartsWithAOrK = employeeList.stream()
                .map(employee -> employee.getName().startsWith("K") || employee.getName().startsWith("A"))
                .reduce(Boolean::logicalAnd)
                .orElse(false);
        System.out.println("allEmpsNameStartsWithAOrK :: " + allEmpsNameStartsWithAOrK);

        //sum
        List<TaxationDetail> taxationDetailsList = getTaxationDetailsList();
        BigDecimal finalTaxAmount = taxationDetailsList.stream().map(taxationDetail -> Optional.ofNullable(taxationDetail).map(TaxationDetail::getTaxAmounts).map(TaxAmounts::getTaxationAmounts).map(TaxationAmounts::getUnitTaxAmount).orElse(BigDecimal.ZERO)).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("finalTaxAmount :: " + finalTaxAmount); //1000.92

        List<ServicePrice> servicePriceList = getAllServicePrices();

        //sum of each savings type (isApplied or not)
        Map<Boolean, BigDecimal> sumOfEachTypeOfSaving =
                servicePriceList.stream().flatMap(servicePrice -> servicePrice.getSavingList().stream())
                        .collect(Collectors.groupingBy(Saving::getIsApplied,
                        Collectors.reducing(BigDecimal.ZERO, Saving::getAmount, BigDecimal::add)));
        System.out.println("sumOfEachTypeOfSaving :: " + sumOfEachTypeOfSaving); //{false=3300, true=1216}

        //Largest word
        List<String> words = Arrays.asList("GFG", "Geeks", "for", "GeeksQuiz", "GeeksforGeeks");
        String largestWord = words.stream().reduce((word1, word2) -> word1.length()>word2.length() ? word1:word2).orElse(null);
        System.out.println("largestWord :: " + largestWord);

    }
}
