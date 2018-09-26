import { element, by, ElementFinder } from 'protractor';

export class ChantierIntervenantComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-chantier-intervenant div table .btn-danger'));
    title = element.all(by.css('jhi-chantier-intervenant div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class ChantierIntervenantUpdatePage {
    pageTitle = element(by.id('jhi-chantier-intervenant-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    corpmetierSelect = element(by.id('field_corpmetier'));
    roleSelect = element(by.id('field_role'));
    contactSelect = element(by.id('field_contact'));
    chantierSelect = element(by.id('field_chantier'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCorpmetierSelect(corpmetier) {
        await this.corpmetierSelect.sendKeys(corpmetier);
    }

    async getCorpmetierSelect() {
        return this.corpmetierSelect.element(by.css('option:checked')).getText();
    }

    async corpmetierSelectLastOption() {
        await this.corpmetierSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setRoleSelect(role) {
        await this.roleSelect.sendKeys(role);
    }

    async getRoleSelect() {
        return this.roleSelect.element(by.css('option:checked')).getText();
    }

    async roleSelectLastOption() {
        await this.roleSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contactSelectLastOption() {
        await this.contactSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contactSelectOption(option) {
        await this.contactSelect.sendKeys(option);
    }

    getContactSelect(): ElementFinder {
        return this.contactSelect;
    }

    async getContactSelectedOption() {
        return this.contactSelect.element(by.css('option:checked')).getText();
    }

    async chantierSelectLastOption() {
        await this.chantierSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async chantierSelectOption(option) {
        await this.chantierSelect.sendKeys(option);
    }

    getChantierSelect(): ElementFinder {
        return this.chantierSelect;
    }

    async getChantierSelectedOption() {
        return this.chantierSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ChantierIntervenantDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-chantierIntervenant-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-chantierIntervenant'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
