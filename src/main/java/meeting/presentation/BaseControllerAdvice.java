package meeting.presentation;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice(basePackageClasses = BaseControllerAdvice.class)
public class BaseControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
        binder.setAllowedFields("");

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    @ExceptionHandler({Exception.class})
    String handle(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return "error";
    }
}
