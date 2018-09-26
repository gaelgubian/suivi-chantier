/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactComponentsPage, ContactDeleteDialog, ContactUpdatePage } from './contact.page-object';

const expect = chai.expect;

describe('Contact e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactUpdatePage: ContactUpdatePage;
    let contactComponentsPage: ContactComponentsPage;
    let contactDeleteDialog: ContactDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Contacts', async () => {
        await navBarPage.goToEntity('contact');
        contactComponentsPage = new ContactComponentsPage();
        expect(await contactComponentsPage.getTitle()).to.eq('Contacts');
    });

    it('should load create Contact page', async () => {
        await contactComponentsPage.clickOnCreateButton();
        contactUpdatePage = new ContactUpdatePage();
        expect(await contactUpdatePage.getPageTitle()).to.eq('Create or edit a Contact');
        await contactUpdatePage.cancel();
    });

    it('should create and save Contacts', async () => {
        const nbButtonsBeforeCreate = await contactComponentsPage.countDeleteButtons();

        await contactComponentsPage.clickOnCreateButton();
        await contactUpdatePage.setLabelInput('label');
        expect(await contactUpdatePage.getLabelInput()).to.eq('label');
        await contactUpdatePage.setLoginInput('login');
        expect(await contactUpdatePage.getLoginInput()).to.eq('login');
        await contactUpdatePage.setPrenomInput('prenom');
        expect(await contactUpdatePage.getPrenomInput()).to.eq('prenom');
        await contactUpdatePage.setNomInput('nom');
        expect(await contactUpdatePage.getNomInput()).to.eq('nom');
        await contactUpdatePage.setEmailInput('email');
        expect(await contactUpdatePage.getEmailInput()).to.eq('email');
        await contactUpdatePage.setTelephoneMobileInput('telephoneMobile');
        expect(await contactUpdatePage.getTelephoneMobileInput()).to.eq('telephoneMobile');
        await contactUpdatePage.setTelephone2Input('telephone2');
        expect(await contactUpdatePage.getTelephone2Input()).to.eq('telephone2');
        await contactUpdatePage.setFaxInput('fax');
        expect(await contactUpdatePage.getFaxInput()).to.eq('fax');
        await contactUpdatePage.setDescriptionInput('description');
        expect(await contactUpdatePage.getDescriptionInput()).to.eq('description');
        await contactUpdatePage.setPosteInput('poste');
        expect(await contactUpdatePage.getPosteInput()).to.eq('poste');
        await contactUpdatePage.corpmetierSelectLastOption();
        await contactUpdatePage.adressecontactSelectLastOption();
        await contactUpdatePage.save();
        expect(await contactUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Contact', async () => {
        const nbButtonsBeforeDelete = await contactComponentsPage.countDeleteButtons();
        await contactComponentsPage.clickOnLastDeleteButton();

        contactDeleteDialog = new ContactDeleteDialog();
        expect(await contactDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Contact?');
        await contactDeleteDialog.clickOnConfirmButton();

        expect(await contactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
