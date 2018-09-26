import { element, by, ElementFinder } from 'protractor';

export class CommentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-comment div table .btn-danger'));
    title = element.all(by.css('jhi-comment div h2#page-heading span')).first();

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

export class CommentUpdatePage {
    pageTitle = element(by.id('jhi-comment-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    commentInput = element(by.id('field_comment'));
    positionxInput = element(by.id('field_positionx'));
    positionyInput = element(by.id('field_positiony'));
    widthInput = element(by.id('field_width'));
    heigthInput = element(by.id('field_heigth'));
    modifiedInput = element(by.id('field_modified'));
    echeanceInput = element(by.id('field_echeance'));
    imageInput = element(by.id('file_image'));
    typeSelect = element(by.id('field_type'));
    stateSelect = element(by.id('field_state'));
    iconSelect = element(by.id('field_icon'));
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

    async setCommentInput(comment) {
        await this.commentInput.sendKeys(comment);
    }

    async getCommentInput() {
        return this.commentInput.getAttribute('value');
    }

    async setPositionxInput(positionx) {
        await this.positionxInput.sendKeys(positionx);
    }

    async getPositionxInput() {
        return this.positionxInput.getAttribute('value');
    }

    async setPositionyInput(positiony) {
        await this.positionyInput.sendKeys(positiony);
    }

    async getPositionyInput() {
        return this.positionyInput.getAttribute('value');
    }

    async setWidthInput(width) {
        await this.widthInput.sendKeys(width);
    }

    async getWidthInput() {
        return this.widthInput.getAttribute('value');
    }

    async setHeigthInput(heigth) {
        await this.heigthInput.sendKeys(heigth);
    }

    async getHeigthInput() {
        return this.heigthInput.getAttribute('value');
    }

    async setModifiedInput(modified) {
        await this.modifiedInput.sendKeys(modified);
    }

    async getModifiedInput() {
        return this.modifiedInput.getAttribute('value');
    }

    async setEcheanceInput(echeance) {
        await this.echeanceInput.sendKeys(echeance);
    }

    async getEcheanceInput() {
        return this.echeanceInput.getAttribute('value');
    }

    async setImageInput(image) {
        await this.imageInput.sendKeys(image);
    }

    async getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    async setTypeSelect(type) {
        await this.typeSelect.sendKeys(type);
    }

    async getTypeSelect() {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    async typeSelectLastOption() {
        await this.typeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setStateSelect(state) {
        await this.stateSelect.sendKeys(state);
    }

    async getStateSelect() {
        return this.stateSelect.element(by.css('option:checked')).getText();
    }

    async stateSelectLastOption() {
        await this.stateSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async iconSelectLastOption() {
        await this.iconSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async iconSelectOption(option) {
        await this.iconSelect.sendKeys(option);
    }

    getIconSelect(): ElementFinder {
        return this.iconSelect;
    }

    async getIconSelectedOption() {
        return this.iconSelect.element(by.css('option:checked')).getText();
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

export class CommentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-comment-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-comment'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
