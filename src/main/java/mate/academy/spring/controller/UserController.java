package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.model.User;
import mate.academy.spring.model.dto.UserResponseDto;
import mate.academy.spring.service.UserService;
import mate.academy.spring.service.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserController(UserService userService,
                          UserDtoMapper userMapper) {
        this.userService = userService;
        this.userDtoMapper = userMapper;
    }

    @GetMapping("/inject")
    public String inject() {
        User firstUser = new User();
        firstUser.setName("Bob");
        firstUser.setLastName("Peters");

        User secondUser = new User();
        secondUser.setName("Anna");
        secondUser.setLastName("Gibson");

        User thirdUser = new User();
        thirdUser.setName("Alica");
        thirdUser.setLastName("Jackson");

        User fourthUser = new User();
        fourthUser.setName("Kate");
        fourthUser.setLastName("Martin");

        userService.add(firstUser);
        userService.add(secondUser);
        userService.add(thirdUser);
        userService.add(fourthUser);
        return "Users was successfully added to the DB";
    }

    @GetMapping("/{userId")
    public UserResponseDto get(@PathVariable Long userId) {
        return userDtoMapper.parse(userService.get(userId));
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll().stream()
                .map(userDtoMapper::parse)
                .collect(Collectors.toList());
    }
}