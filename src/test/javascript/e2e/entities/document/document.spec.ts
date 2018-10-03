/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DocumentComponentsPage, DocumentDeleteDialog, DocumentUpdatePage } from './document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Document e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let documentUpdatePage: DocumentUpdatePage;
    let documentComponentsPage: DocumentComponentsPage;
    let documentDeleteDialog: DocumentDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Documents', async () => {
        await navBarPage.goToEntity('document');
        documentComponentsPage = new DocumentComponentsPage();
        expect(await documentComponentsPage.getTitle()).to.eq('Documents');
    });

    it('should load create Document page', async () => {
        await documentComponentsPage.clickOnCreateButton();
        documentUpdatePage = new DocumentUpdatePage();
        expect(await documentUpdatePage.getPageTitle()).to.eq('Create or edit a Document');
        await documentUpdatePage.cancel();
    });

    it('should create and save Documents', async () => {
        const nbButtonsBeforeCreate = await documentComponentsPage.countDeleteButtons();

        await documentComponentsPage.clickOnCreateButton();
        await documentUpdatePage.setLabelInput('label');
        expect(await documentUpdatePage.getLabelInput()).to.eq('label');
        await documentUpdatePage.setDescriptionInput('description');
        expect(await documentUpdatePage.getDescriptionInput()).to.eq('description');
        await documentUpdatePage.setPathInput('path');
        expect(await documentUpdatePage.getPathInput()).to.eq('path');
        await documentUpdatePage.setFilenameInput('filename');
        expect(await documentUpdatePage.getFilenameInput()).to.eq('filename');
        await documentUpdatePage.setContentInput(absolutePath);
        await documentUpdatePage.chantierSelectLastOption();
        await documentUpdatePage.bienSelectLastOption();
        await documentUpdatePage.visiteSelectLastOption();
        await documentUpdatePage.save();
        expect(await documentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await documentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Document', async () => {
        const nbButtonsBeforeDelete = await documentComponentsPage.countDeleteButtons();
        await documentComponentsPage.clickOnLastDeleteButton();

        documentDeleteDialog = new DocumentDeleteDialog();
        expect(await documentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Document?');
        await documentDeleteDialog.clickOnConfirmButton();

        expect(await documentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
