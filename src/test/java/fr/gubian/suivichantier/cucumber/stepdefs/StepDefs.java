package fr.gubian.suivichantier.cucumber.stepdefs;

import fr.gubian.suivichantier.SuiviChantierApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = SuiviChantierApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
