import { element, by, ElementFinder } from 'protractor';

export class DocumentTuileComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-document-tuile div table .btn-danger'));
    title = element.all(by.css('jhi-document-tuile div h2#page-heading span')).first();

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

export class DocumentTuileUpdatePage {
    pageTitle = element(by.id('jhi-document-tuile-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    contentInput = element(by.id('file_content'));
    documentSelect = element(by.id('field_document'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setContentInput(content) {
        await this.contentInput.sendKeys(content);
    }

    async getContentInput() {
        return this.contentInput.getAttribute('value');
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

export class DocumentTuileDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-documentTuile-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-documentTuile'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
