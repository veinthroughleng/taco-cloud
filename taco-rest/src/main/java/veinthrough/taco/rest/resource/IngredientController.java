package veinthrough.taco.rest.resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static veinthrough.taco.utils.Constants.JSON;
import static veinthrough.taco.utils.Constants.PATH_INGREDIENTS;

@RestController
@RequestMapping(path = PATH_INGREDIENTS,
        produces = JSON,
        consumes = JSON)
@CrossOrigin(origins = "*")
class IngredientController {}
