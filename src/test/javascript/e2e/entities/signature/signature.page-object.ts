import { element, by, ElementFinder } from 'protractor';

export class SignatureComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-signature div table .btn-danger'));
    title = element.all(by.css('jhi-signature div h2#page-heading span')).first();

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

export class SignatureUpdatePage {
    pageTitle = element(by.id('jhi-signature-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    imageInput = element(by.id('file_image'));
    chantierIntervenantSelect = element(by.id('field_chantierIntervenant'));
    documentsSelect = element(by.id('field_documents'));
    visiteSelect = element(by.id('field_visite'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setImageInput(image) {
        await this.imageInput.sendKeys(image);
    }

    async getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    async chantierIntervenantSelectLastOption() {
        await this.chantierIntervenantSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async chantierIntervenantSelectOption(option) {
        await this.chantierIntervenantSelect.sendKeys(option);
    }

    getChantierIntervenantSelect(): ElementFinder {
        return this.chantierIntervenantSelect;
    }

    async getChantierIntervenantSelectedOption() {
        return this.chantierIntervenantSelect.element(by.css('option:checked')).getText();
    }

    async documentsSelectLastOption() {
        await this.documentsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async documentsSelectOption(option) {
        await this.documentsSelect.sendKeys(option);
    }

    getDocumentsSelect(): ElementFinder {
        return this.documentsSelect;
    }

    async getDocumentsSelectedOption() {
        return this.documentsSelect.element(by.css('option:checked')).getText();
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

export class SignatureDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-signature-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-signature'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
