/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ChantierComponentsPage, ChantierDeleteDialog, ChantierUpdatePage } from './chantier.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Chantier e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let chantierUpdatePage: ChantierUpdatePage;
    let chantierComponentsPage: ChantierComponentsPage;
    let chantierDeleteDialog: ChantierDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Chantiers', async () => {
        await navBarPage.goToEntity('chantier');
        chantierComponentsPage = new ChantierComponentsPage();
        expect(await chantierComponentsPage.getTitle()).to.eq('Chantiers');
    });

    it('should load create Chantier page', async () => {
        await chantierComponentsPage.clickOnCreateButton();
        chantierUpdatePage = new ChantierUpdatePage();
        expect(await chantierUpdatePage.getPageTitle()).to.eq('Create or edit a Chantier');
        await chantierUpdatePage.cancel();
    });

    it('should create and save Chantiers', async () => {
        const nbButtonsBeforeCreate = await chantierComponentsPage.countDeleteButtons();

        await chantierComponentsPage.clickOnCreateButton();
        await chantierUpdatePage.setLabelInput('label');
        expect(await chantierUpdatePage.getLabelInput()).to.eq('label');
        await chantierUpdatePage.setImageInput(absolutePath);
        await chantierUpdatePage.setDescriptionInput('description');
        expect(await chantierUpdatePage.getDescriptionInput()).to.eq('description');
        await chantierUpdatePage.setDateDebutInput('2000-12-31');
        expect(await chantierUpdatePage.getDateDebutInput()).to.eq('2000-12-31');
        await chantierUpdatePage.setDateFinInput('2000-12-31');
        expect(await chantierUpdatePage.getDateFinInput()).to.eq('2000-12-31');
        await chantierUpdatePage.save();
        expect(await chantierUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await chantierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Chantier', async () => {
        const nbButtonsBeforeDelete = await chantierComponentsPage.countDeleteButtons();
        await chantierComponentsPage.clickOnLastDeleteButton();

        chantierDeleteDialog = new ChantierDeleteDialog();
        expect(await chantierDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Chantier?');
        await chantierDeleteDialog.clickOnConfirmButton();

        expect(await chantierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
