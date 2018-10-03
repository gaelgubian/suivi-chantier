import { element, by, ElementFinder } from 'protractor';

export class DocumentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-document div table .btn-danger'));
    title = element.all(by.css('jhi-document div h2#page-heading span')).first();

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

export class DocumentUpdatePage {
    pageTitle = element(by.id('jhi-document-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    descriptionInput = element(by.id('field_description'));
    pathInput = element(by.id('field_path'));
    filenameInput = element(by.id('field_filename'));
    contentInput = element(by.id('file_content'));
    chantierSelect = element(by.id('field_chantier'));
    bienSelect = element(by.id('field_bien'));
    visiteSelect = element(by.id('field_visite'));

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

    async setPathInput(path) {
        await this.pathInput.sendKeys(path);
    }

    async getPathInput() {
        return this.pathInput.getAttribute('value');
    }

    async setFilenameInput(filename) {
        await this.filenameInput.sendKeys(filename);
    }

    async getFilenameInput() {
        return this.filenameInput.getAttribute('value');
    }

    async setContentInput(content) {
        await this.contentInput.sendKeys(content);
    }

    async getContentInput() {
        return this.contentInput.getAttribute('value');
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

    async visiteSelectLastOption() {
        await this.visiteSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async visiteSelectOption(option) {
        await this.visiteSelect.sendKeys(option);
    }

    getVisiteSelect(): ElementFinder {
        return this.visiteSelect;
    }

    async getVisiteSelectedOption() {
        return this.visiteSelect.element(by.css('option:checked')).getText();
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

export class DocumentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-document-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-document'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
