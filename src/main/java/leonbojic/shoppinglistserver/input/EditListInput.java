package leonbojic.shoppinglistserver.input;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class EditListInput {
    private String name;
    private LocalDate date;
}
