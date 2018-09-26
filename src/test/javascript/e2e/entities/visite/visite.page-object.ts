import { element, by, ElementFinder } from 'protractor';

export class VisiteComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-visite div table .btn-danger'));
    title = element.all(by.css('jhi-visite div h2#page-heading span')).first();

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

export class VisiteUpdatePage {
    pageTitle = element(by.id('jhi-visite-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    dateInput = element(by.id('field_date'));
    bienSelect = element(by.id('field_bien'));
    documentSelect = element(by.id('field_document'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setLabelInput(label) {
        await this.labelInput.sendKeys(label);
    }

    async getLabelInput() {
        return this.labelInput.getAttribute('value');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async bienSelectLastOption() {
        await this.bienSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async bienSelectOption(option) {
        await this.bienSelect.sendKeys(option);
    }

    getBienSelect(): ElementFinder {
        return this.bienSelect;
    }

    async getBienSelectedOption() {
        return this.bienSelect.element(by.css('option:checked')).getText();
    }

    async documentSelectLastOption() {
        await this.documentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async documentSelectOption(option) {
        await this.documentSelect.sendKeys(option);
    }

    getDocumentSelect(): ElementFinder {
        return this.documentSelect;
    }

    async getDocumentSelectedOption() {
        return this.documentSelect.element(by.css('option:checked')).getText();
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

export class VisiteDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-visite-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-visite'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
