function check_email(email){
    // split email at the @ symbol
    var emailParts = email.split("@");

    // if email is not a student.hu.nl email address show alert and return false
    if (emailParts[1] !== "student.hu.nl") {
        console.log("Je email moet een student.hu.nl adres zijn");
        return false;
    }

    return true;
}



test('check email', () => {
    expect(check_email("olav.kuhnen@student.hu.nl")).toBe(true);
});

test('check email', () => {
    expect(check_email("olav.kuhnen@hotmail.com")).toBe(true);
});

test('check email', () => {
    expect(check_email("")).toBe(true);
});

