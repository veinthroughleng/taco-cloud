package veinthrough.tacocloud.rest.resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static veinthrough.utils.Constants.JSON;
import static veinthrough.utils.Constants.PATH_INGREDIENT;

@RestController
@RequestMapping(path = PATH_INGREDIENT,
        produces = JSON,
        consumes = JSON)
@CrossOrigin(origins = "*")
public class IngredientController {}
