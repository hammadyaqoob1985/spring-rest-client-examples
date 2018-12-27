package guru.springframework.api.domain;

import lombok.Data;

@Data
public class Card {
    private String type;
    private String number;
    private String iban;
    private String swift;
    private ExperiationData experiationData;
}
