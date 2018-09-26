/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IconComponentsPage, IconDeleteDialog, IconUpdatePage } from './icon.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Icon e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let iconUpdatePage: IconUpdatePage;
    let iconComponentsPage: IconComponentsPage;
    let iconDeleteDialog: IconDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Icons', async () => {
        await navBarPage.goToEntity('icon');
        iconComponentsPage = new IconComponentsPage();
        expect(await iconComponentsPage.getTitle()).to.eq('Icons');
    });

    it('should load create Icon page', async () => {
        await iconComponentsPage.clickOnCreateButton();
        iconUpdatePage = new IconUpdatePage();
        expect(await iconUpdatePage.getPageTitle()).to.eq('Create or edit a Icon');
        await iconUpdatePage.cancel();
    });

    it('should create and save Icons', async () => {
        const nbButtonsBeforeCreate = await iconComponentsPage.countDeleteButtons();

        await iconComponentsPage.clickOnCreateButton();
        await iconUpdatePage.setLabelInput('label');
        expect(await iconUpdatePage.getLabelInput()).to.eq('label');
        await iconUpdatePage.setImageInput(absolutePath);
        await iconUpdatePage.save();
        expect(await iconUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await iconComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Icon', async () => {
        const nbButtonsBeforeDelete = await iconComponentsPage.countDeleteButtons();
        await iconComponentsPage.clickOnLastDeleteButton();

        iconDeleteDialog = new IconDeleteDialog();
        expect(await iconDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Icon?');
        await iconDeleteDialog.clickOnConfirmButton();

        expect(await iconComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
