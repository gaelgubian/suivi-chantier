/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DocumentTuileComponentsPage, DocumentTuileDeleteDialog, DocumentTuileUpdatePage } from './document-tuile.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('DocumentTuile e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let documentTuileUpdatePage: DocumentTuileUpdatePage;
    let documentTuileComponentsPage: DocumentTuileComponentsPage;
    let documentTuileDeleteDialog: DocumentTuileDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load DocumentTuiles', async () => {
        await navBarPage.goToEntity('document-tuile');
        documentTuileComponentsPage = new DocumentTuileComponentsPage();
        expect(await documentTuileComponentsPage.getTitle()).to.eq('Document Tuiles');
    });

    it('should load create DocumentTuile page', async () => {
        await documentTuileComponentsPage.clickOnCreateButton();
        documentTuileUpdatePage = new DocumentTuileUpdatePage();
        expect(await documentTuileUpdatePage.getPageTitle()).to.eq('Create or edit a Document Tuile');
        await documentTuileUpdatePage.cancel();
    });

    it('should create and save DocumentTuiles', async () => {
        const nbButtonsBeforeCreate = await documentTuileComponentsPage.countDeleteButtons();

        await documentTuileComponentsPage.clickOnCreateButton();
        await documentTuileUpdatePage.setContentInput(absolutePath);
        await documentTuileUpdatePage.documentSelectLastOption();
        await documentTuileUpdatePage.save();
        expect(await documentTuileUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await documentTuileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last DocumentTuile', async () => {
        const nbButtonsBeforeDelete = await documentTuileComponentsPage.countDeleteButtons();
        await documentTuileComponentsPage.clickOnLastDeleteButton();

        documentTuileDeleteDialog = new DocumentTuileDeleteDialog();
        expect(await documentTuileDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Document Tuile?');
        await documentTuileDeleteDialog.clickOnConfirmButton();

        expect(await documentTuileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
