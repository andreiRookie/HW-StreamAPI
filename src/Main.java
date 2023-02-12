import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Person> persons = getPersonsList();

        long underageCount = getUnderagePersonsCount(persons);
        System.out.println("underageCount: " + underageCount);

        List<String> recruitSurnamesList = getRecruitSurnamesList(persons);
        System.out.println("recruitSurnamesList size: " + recruitSurnamesList.size() + ":");
        printSelection(recruitSurnamesList);

        List<Person> employablePersonsList = getEmployablePersonsList(persons);
        System.out.println("employablePersonsList size: " + employablePersonsList.size() + ":");
        printSelection(employablePersonsList);

    }

    private static List<Person> getEmployablePersonsList(List<Person> list) {
        Predicate<Person> isPersonEmployable =
                (Person p) -> (p.getSex() == Sex.FEMALE && p.getAge() >= 18 && p.getAge() < 60)
                        || (p.getSex() == Sex.MALE && p.getAge() >= 18 && p.getAge() < 65);

        Comparator<Person> personSurnameComparator =
                Comparator.comparing(Person::getFamily);

        return list.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(isPersonEmployable)
                .sorted(personSurnameComparator)
                .toList();
    }

    private static List<String> getRecruitSurnamesList(List<Person> list) {
        return list.stream()
                .filter(person -> person.getSex() == Sex.MALE)
                .filter(person -> ( person.getAge() >= 18 && person.getAge() < 27) )
                .map(Person::getFamily)
                .collect(Collectors.toList());
    }

    private static long getUnderagePersonsCount(List<Person> list) {
        return list.stream()
                .filter(person -> person.getAge() < 18)
                .count();
    }

    private static List<Person> getPersonsList() {
        Random random = new Random();

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> surnames = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_0000; i++) {
            persons.add(new Person(
                    names.get(random.nextInt(names.size())),
                    surnames.get(random.nextInt(surnames.size())),
                    random.nextInt(100),
                    Sex.values()[random.nextInt(Sex.values().length)],
                    Education.values()[random.nextInt(Education.values().length)]));
        }
        return persons;
    }

    private static <T> void printSelection(List<T> list) {
        for (int i = 0; i < list.size(); i += 150000) {
            System.out.println(list.get(i));
        }
    }
}