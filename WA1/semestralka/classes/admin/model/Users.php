<?php

namespace admin\model;

/**
 * Description of Users
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Users extends ModelClass {

    private $users;

    protected function load() {
        if (!$this->loggedIn()->isAdmin()) {
            throw new \model\NotFoundException();
        }
        if (isset($_GET['deleteUser'])) {
            try {
                $this->status = $this->deleteUser(intval(filter_input(INPUT_GET, 'deleteUser')));
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else {
            $this->status = NULL;
        }
        $this->users = \UsersTable::getInstance()->getAll();
    }

    private function deleteUser($id) {
        $user = \UsersTable::getInstance()->getByID($id);
        if ($user != NULL) {
            $galleries = \GalleriesTable::getInstance()->getForUser($user, 0, 0);
            foreach ($galleries as $gallery) {
                \Config::getInstance()->deleteDir(\Config::getInstance()->getImageUploadDirForGallery($gallery['id']));
            }
            $user->delete();
        }
    }

    /**
     * Returns users to display.
     * 
     * @return array
     */
    public function getUsers() {
        return $this->users;
    }

}
