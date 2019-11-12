package dto;

import lombok.*;

@Getter
@Builder
public class UserEntityDto {
    private Long id;
    private String name;
    private String lastName;
    private  String age;
}
