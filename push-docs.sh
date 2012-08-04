#!/bin/sh
cd docs

make html
make pdf

cd ..

git stash
git stash drop
git checkout gh-pages

cp -r docs/_build/html/* .
rm -rf target/ docs/

git add .
git ci -m "Updating documentation @ $(date)"
git push origin gh-pages

echo "Done!"
git checkout master
