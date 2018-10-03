import { element, by, ElementFinder } from 'protractor';

export class BienComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-bien div table .btn-danger'));
    title = element.all(by.css('jhi-bien div h2#page-heading span')).first();

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

export class BienUpdatePage {
    pageTitle = element(by.id('jhi-bien-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    descriptionInput = element(by.id('field_description'));
    adresseBienSelect = element(by.id('field_adresseBien'));
    chantierSelect = element(by.id('field_chantier'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setLabelInput(label) {
        await this.labelInput.sendKeys(label);
    }

    async getLabelInput() {
        return this.labelInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async adresseBienSelectLastOption() {
        await this.adresseBienSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async adresseBienSelectOption(option) {
        await this.adresseBienSelect.sendKeys(option);
    }

    getAdresseBienSelect(): ElementFinder {
        return this.adresseBienSelect;
    }

    async getAdresseBienSelectedOption() {
        return this.adresseBienSelect.element(by.css('option:checked')).getText();
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

export class BienDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-bien-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-bien'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
