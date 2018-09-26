/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SignatureComponentsPage, SignatureDeleteDialog, SignatureUpdatePage } from './signature.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Signature e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let signatureUpdatePage: SignatureUpdatePage;
    let signatureComponentsPage: SignatureComponentsPage;
    let signatureDeleteDialog: SignatureDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Signatures', async () => {
        await navBarPage.goToEntity('signature');
        signatureComponentsPage = new SignatureComponentsPage();
        expect(await signatureComponentsPage.getTitle()).to.eq('Signatures');
    });

    it('should load create Signature page', async () => {
        await signatureComponentsPage.clickOnCreateButton();
        signatureUpdatePage = new SignatureUpdatePage();
        expect(await signatureUpdatePage.getPageTitle()).to.eq('Create or edit a Signature');
        await signatureUpdatePage.cancel();
    });

    it('should create and save Signatures', async () => {
        const nbButtonsBeforeCreate = await signatureComponentsPage.countDeleteButtons();

        await signatureComponentsPage.clickOnCreateButton();
        await signatureUpdatePage.setImageInput(absolutePath);
        await signatureUpdatePage.chantierIntervenantSelectLastOption();
        await signatureUpdatePage.documentSelectLastOption();
        await signatureUpdatePage.visiteSelectLastOption();
        await signatureUpdatePage.save();
        expect(await signatureUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await signatureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Signature', async () => {
        const nbButtonsBeforeDelete = await signatureComponentsPage.countDeleteButtons();
        await signatureComponentsPage.clickOnLastDeleteButton();

        signatureDeleteDialog = new SignatureDeleteDialog();
        expect(await signatureDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Signature?');
        await signatureDeleteDialog.clickOnConfirmButton();

        expect(await signatureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
